import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ReasonTypeDetailComponent } from 'app/entities/reason-type/reason-type-detail.component';
import { ReasonType } from 'app/shared/model/reason-type.model';

describe('Component Tests', () => {
  describe('ReasonType Management Detail Component', () => {
    let comp: ReasonTypeDetailComponent;
    let fixture: ComponentFixture<ReasonTypeDetailComponent>;
    const route = ({ data: of({ reasonType: new ReasonType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ReasonTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ReasonTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReasonTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load reasonType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reasonType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
