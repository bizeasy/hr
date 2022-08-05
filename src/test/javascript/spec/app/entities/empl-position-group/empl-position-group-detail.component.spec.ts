import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmplPositionGroupDetailComponent } from 'app/entities/empl-position-group/empl-position-group-detail.component';
import { EmplPositionGroup } from 'app/shared/model/empl-position-group.model';

describe('Component Tests', () => {
  describe('EmplPositionGroup Management Detail Component', () => {
    let comp: EmplPositionGroupDetailComponent;
    let fixture: ComponentFixture<EmplPositionGroupDetailComponent>;
    const route = ({ data: of({ emplPositionGroup: new EmplPositionGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmplPositionGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmplPositionGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load emplPositionGroup on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emplPositionGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
