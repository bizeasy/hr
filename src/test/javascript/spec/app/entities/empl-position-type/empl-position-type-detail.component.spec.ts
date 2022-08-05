import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmplPositionTypeDetailComponent } from 'app/entities/empl-position-type/empl-position-type-detail.component';
import { EmplPositionType } from 'app/shared/model/empl-position-type.model';

describe('Component Tests', () => {
  describe('EmplPositionType Management Detail Component', () => {
    let comp: EmplPositionTypeDetailComponent;
    let fixture: ComponentFixture<EmplPositionTypeDetailComponent>;
    const route = ({ data: of({ emplPositionType: new EmplPositionType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmplPositionTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmplPositionTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load emplPositionType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emplPositionType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
