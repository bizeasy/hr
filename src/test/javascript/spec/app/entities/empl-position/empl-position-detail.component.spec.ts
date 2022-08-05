import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmplPositionDetailComponent } from 'app/entities/empl-position/empl-position-detail.component';
import { EmplPosition } from 'app/shared/model/empl-position.model';

describe('Component Tests', () => {
  describe('EmplPosition Management Detail Component', () => {
    let comp: EmplPositionDetailComponent;
    let fixture: ComponentFixture<EmplPositionDetailComponent>;
    const route = ({ data: of({ emplPosition: new EmplPosition(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmplPositionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmplPositionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load emplPosition on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emplPosition).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
